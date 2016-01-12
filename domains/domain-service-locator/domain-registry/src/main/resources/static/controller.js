(function() {
    var AppController = function($scope) {
        $scope.domains = [];
        /* todo : to change
        $scope.currentWorkflow = createSubscription();*/
        //$scope.currentStep = angular. $scope.currentWorkflow.steps.get(1);
        $scope.lookingForDomain = function() {
            console.log("looking for domain");
            $scope.domains = [ new Domain("policy", "policy-service"),
                new Domain("events", "business-events")];
            console.log(">>> " + $scope.domains);
        };
    };

    function Domain(name, location) {
        var name = name;
        var location = location;
    }

    AppController.$inject = ['$scope'];
    angular.module("container").controller("AppController", AppController);
}());