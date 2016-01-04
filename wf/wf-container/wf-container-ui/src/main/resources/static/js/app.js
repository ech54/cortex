(function() {
    'use strict';
    angular.module("container.controllers", []);
    angular.module("container.services", []);
    angular.module("container", ["container.controllers", "container.services"]);

    angular.module("container").directive("displayStep", function(){
        return {
            template: '<ng-include src="getTemplateUrl()"/>',
        scope: {
            step: '=step',
            event: '=event'
        },
        restrict: 'E',
        controller: function($scope) {
            $scope.getTemplateUrl = function() {
                return '' + $scope.step.template + ".html";
            }
        }};
    });

    angular.module("container").filter('byCurrentStep', function(){
        console.info('filter');
        return function(obj){
            console.info('>>>' + obj);
            console.info('>>>' + obj.display);
            return obj.display;
        }
    });
}());
//}(angular));