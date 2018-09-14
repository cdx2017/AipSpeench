var app = angular.module('app', []);
app.controller('MainController', function ($rootScope, $scope, $http) {

        $scope.data = {};

        //登录
        $scope.login = function () {
            $http({
                url: '/route/login_in',
                method: 'POST',
                data: $scope.data
            }).success(function (result) {
                    if (result.length != 0 && result.username == $scope.data.username
                        && result.password == $scope.data.password) {
                        self.location = result.nextgo;
                    } else
                        alert("用户名或密码错误！")
                }
            );
        };

    }
)
;