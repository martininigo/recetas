var app = angular.module("app", ['ngRoute']);

function RemoteResource($http, $q, baseUrl) {
//    this.get = function(idSeguro) {
//        var defered = $q.defer();
//        var promise = defered.promise;
//
//        $http({
//            method: 'GET',
//            url: baseUrl + '/api/SeguroMedico/' + idSeguro
//        }).success(function(data, status, headers, config) {
//            defered.resolve(data);
//        }).error(function(data, status, headers, config) {
//            if (status === 400) {
//                defered.reject(data);
//            } else {
//                throw new Error("Fallo obtener los datos:" + status + "\n" + data);
//            }
//        });
//
//        return promise;
//
//    };

    this.list = function() {
        var defered = $q.defer();
        var promise = defered.promise;

        $http({
            method: 'GET',
            url: baseUrl + '/../api/insumo'
        }).success(function(data, status, headers, config) {
            defered.resolve(data);
        }).error(function(data, status, headers, config) {
            if (status === 400) {
                defered.reject(data);
            } else {
                throw new Error("Fallo obtener los datos:" + status + "\n" + data);
            }
        });


        return promise;
    };

//    this.insert = function(seguroMedico) {
//        var defered = $q.defer();
//        var promise = defered.promise;
//
//        $http({
//            method: 'POST',
//            url: baseUrl + '/api/SeguroMedico',
//            data: seguroMedico
//        }).success(function(data, status, headers, config) {
//            defered.resolve(data);
//        }).error(function(data, status, headers, config) {
//            if (status === 400) {
//                defered.reject(data);
//            } else {
//                throw new Error("Fallo obtener los datos:" + status + "\n" + data);
//            }
//        });
//
//        return promise;
//    };
//
//    this.update = function(idSeguro, seguroMedico) {
//        var defered = $q.defer();
//        var promise = defered.promise;
//
//        $http({
//            method: 'PUT',
//            url: baseUrl + '/api/SeguroMedico/' + idSeguro,
//            data: seguroMedico
//        }).success(function(data, status, headers, config) {
//            defered.resolve(data);
//        }).error(function(data, status, headers, config) {
//            if (status === 400) {
//                defered.reject(data);
//            } else {
//                throw new Error("Fallo obtener los datos:" + status + "\n" + data);
//            }
//        });
//
//        return promise;
//    };
//
//    this.delete = function(idSeguro) {
//        var defered = $q.defer();
//        var promise = defered.promise;
//
//        $http({
//            method: 'DELETE',
//            url: baseUrl + '/api/SeguroMedico/' + idSeguro
//        }).success(function(data, status, headers, config) {
//            defered.resolve(data);
//        }).error(function(data, status, headers, config) {
//            if (status === 400) {
//                defered.reject(data);
//            } else {
//                throw new Error("Fallo obtener los datos:" + status + "\n" + data);
//            }
//        });
//
//        return promise;
//    };

}

function RemoteResourceProvider() {
    var _baseUrl;
    this.setBaseUrl = function(baseUrl) {
        _baseUrl = baseUrl;
    };
    this.$get = ['$http', '$q', function($http, $q) {
            return new RemoteResource($http, $q, _baseUrl);
        }];
};

app.provider("remoteResource", RemoteResourceProvider);


app.value("urlLogo", "https://scontent-arn2-1.xx.fbcdn.net/v/t1.0-1/c70.0.200.200/p200x200/73612_213162408807358_389884659_n.jpg?oh=8fc720868b89c64878e621e6e136d709&oe=5905014D");
app.run(["$rootScope", "urlLogo", function($rootScope, urlLogo) {
        $rootScope.urlLogo = urlLogo;
    }]);

app.config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/', {
        templateUrl: "main.html",
        controller: "MainController"
    });

    $routeProvider.when('/insumo/list', {
        templateUrl: "insumo/insumo-list.html",
        controller: "InsumoListController",
        resolve: {
            insumos: ['remoteResource', function(remoteResource) {
                    return remoteResource.list();
                }]
        }
    });


    $routeProvider.otherwise({
        redirectTo: '/'
    });

}]);

app.controller("InsumoListController", ['$scope', 'insumos', 'remoteResource', function($scope, insumos, remoteResource) {
    $scope.insumos = insumos;

    $scope.borrar = function(id) {
        remoteResource.delete(id).then(function() {
            remoteResource.list().then(function(insumos) {
                $scope.insumos = insumos;
            }, function(bussinessMessages) {
                $scope.bussinessMessages = bussinessMessages;
            });
        }, function(bussinessMessages) {
            $scope.bussinessMessages = bussinessMessages;
        });
    };

}]);

app.controller("MainController", ['$scope', function($scope) {

}]);