(function() {
    var AppController = function($scope) {

        /* todo : to change
        $scope.currentWorkflow = createSubscription();*/
        //$scope.currentStep = angular. $scope.currentWorkflow.steps.get(1);

    };

    AppController.$inject = ['$scope'];
    angular.module("container").controller("AppController", AppController);
}());