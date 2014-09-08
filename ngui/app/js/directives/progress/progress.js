var module = angular.module(PKG.name+'.directives');

module.directive('myProgress', function myProgressDirective () {
  return {
    restrict: 'E',
    templateUrl: function(element, attrs) {
      return 'progress/'+ (attrs.type||'bar') +'.tpl';
    },
    replace: true,
    scope: {
      addCls: '@',
      value: '=',
      max: '=' 
    },
    link: function(scope, element, attrs) {

      scope.$watch('value', function(newVal) {
        var max = parseInt(scope.max, 10) || 100;

        scope.percent = Math.floor((newVal / max) * 100);

        var cls = {
          'active progress-bar-striped': (newVal < max),
          'progress-bar': true
        };

        if(scope.addCls) {
          angular.forEach(scope.addCls.split(' '), function(add) {
            if(add) {
              switch (attrs.type) {
                case 'bar':
                default:
                  cls['progress-bar-'+add] = true;
                  break;
              }
            }
          });
        }


        scope.cls = cls;
      });
    }

  };
});
