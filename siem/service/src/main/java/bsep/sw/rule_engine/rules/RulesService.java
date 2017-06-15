package bsep.sw.rule_engine.rules;


import bsep.sw.domain.AlarmDefinition;
import bsep.sw.domain.Log;
import bsep.sw.domain.Project;
import bsep.sw.services.AlarmDefinitionService;
import bsep.sw.services.AlarmService;
import bsep.sw.services.ProjectService;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesService {

    private final SimpMessagingTemplate template;
    private final AlarmService alarmService;
    private final ProjectService projectService;
    private final AlarmDefinitionService alarmDefinitionService;

    @Autowired
    public RulesService(final SimpMessagingTemplate template,
                        final AlarmService alarmService,
                        final ProjectService projectService,
                        final AlarmDefinitionService alarmDefinitionService) {
        this.template = template;
        this.alarmService = alarmService;
        this.projectService = projectService;
        this.alarmDefinitionService = alarmDefinitionService;
    }

    public void evaluateNewLog(final Log log) {
        // TODO setup this and read from DB
        final RulesEngine rulesEngine = RulesEngineBuilder
                .aNewRulesEngine()
                .named("Simple rules engine")
                .withSkipOnFirstAppliedRule(false)
                .withSkipOnFirstNonTriggeredRule(false)
                .withSkipOnFirstFailedRule(false)
                .withSilentMode(true)
                .build();

        final Project project = projectService.findOne(log.getProject());

        // don't risk
        if (project == null) return;

        final List<AlarmDefinition> definitions = alarmDefinitionService.findAllByProject(project);

        for (final AlarmDefinition definition : definitions) {
            final SingleLogRule logRule = new SingleLogRule(log, definition, projectService, alarmService, alarmDefinitionService, template);
            rulesEngine.registerRule(logRule);
        }

        rulesEngine.fireRules();
    }
}
