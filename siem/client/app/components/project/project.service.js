(function() {
    'use strict';

    angular
        .module('soteria-app')
        .service('projectService', projectService);

    projectService.$inject = ['CONFIG', '$http', '$log'];

    function projectService(CONFIG, $http, $log) {
        var service = {
            getProjectById: getProjectById,
            getLogs: getLogs,
            getAgents: getAgents
        };

        return service;

        function getProjectById(id) {
            return $http.get(CONFIG.SERVICE_URL + '/projects/' + id)
                .then(function successCallback(response) {
                    return response.data;
                }, function errorCallback(response) {
                    $log.warn(response.data.detail);
                    throw response.data.detail;
                });
        }

        function getLogs(url) {
            return $http.get(url)
                .then(function successCallback(response) {
                    return response.data;
                }, function errorCallback(response) {
                    $log.warn(response.data.detail);
                    throw response.data.detail;
                });
        }

        function getAgents(url) {
            return $http.get(url)
                .then(function successCallback(response) {
                    return response.data;
                }, function errorCallback(response) {
                    $log.warn(response.data.detail);
                    throw response.data.detail;
                });
        }
    }
})();
