(function() {
    var AppController = function($scope) {

        /* todo : to change */
        $scope.currentWorkflow = createSubscription();
        //$scope.currentStep = angular. $scope.currentWorkflow.steps.get(1);

        $scope.nextStep = function(step) {
            $scope.currentWorkflow.steps[step.id].display = false;
            if ($scope.currentWorkflow.steps.length > step.id+1) {
                $scope.currentWorkflow.steps[step.id+1].display = true;
            }
        };
        $scope.previousStep = function(step) {
            $scope.currentWorkflow.steps[step.id].display = false;
            if (step.id-1 >= 0) {
                $scope.currentWorkflow.steps[step.id-1].display = true;
            }
        }
    };

    function createSubscription() {
        var wrk = new Workflow();
        wrk.name = "subscription";
        wrk.id = 1;
        wrk.event = createDefaultEvent();

        var tp = new Step();
        tp.name = "Third party";
        tp.id = 0;
        tp.template = "tpStep";
        tp.ctrl = "tpCtrl";
        tp.display = true;

        var covers = new Step();
        covers.name = "Covers";
        covers.id = 1;
        covers.template = "coversStep";
        covers.ctrl = "coversCtrl";
        covers.display = false;

        var pricing = new Step();
        pricing.name = "Pricing";
        pricing.id = 2;
        pricing.template = "pricingStep";
        pricing.ctrl = "PricingCtrl";
        pricing.display = false;

        var investment = new Step();
        investment.name = "Investment";
        investment.id = 3;
        investment.template = "investmentStep";
        investment.ctrl = "InvestmentCtrl";
        investment.display = false;

        var document = new Step();
        document.name = "Document";
        document.id = 4;
        document.template = "documentStep";
        document.ctrl = "DocumentCtrl";
        document.display = false;

        wrk.steps = [tp, covers, pricing, investment, document];
        return wrk;
    }


    function createDefaultEvent() {
        var e = new Event();
        e.id = 100;
        e.tps = {};
        return e;
    }

    function Event() {
        var id;
        var tps;
    }

    function Workflow() {
        var name;
        var id;
        var event;
        var steps = {};
    }

    function Step() {
        var name;
        var id;
        var ctrl;
        var template;
        var description;
        var link;
        var display = false;
    }

    AppController.$inject = ['$scope'];
    angular.module("container").controller("AppController", AppController);
}());