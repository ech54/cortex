(function() {
    var tpCtrl = function($scope) {
        $scope.event.tps = createTps();
    };

    function createTps() {
        var willySagnol =  new Tp();
        willySagnol.name = "willy Sagnol";
        willySagnol.id = 1;

        var lucyMerry =  new Tp();
        lucyMerry.name = "Lucy Merry";
        lucyMerry.id = 2;

        return {"1" : willySagnol, "2" : lucyMerry};
    }

    function Tp() {
        var name = name;
        var id = id;
    }

    tpCtrl.$inject = ['$scope'];
    angular.module("container").controller("tpCtrl", tpCtrl);
}());