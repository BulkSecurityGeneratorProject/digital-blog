'use strict';

describe('Controller Tests', function() {

    describe('RolePermiso Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRolePermiso, MockPermiso, MockRol;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRolePermiso = jasmine.createSpy('MockRolePermiso');
            MockPermiso = jasmine.createSpy('MockPermiso');
            MockRol = jasmine.createSpy('MockRol');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RolePermiso': MockRolePermiso,
                'Permiso': MockPermiso,
                'Rol': MockRol
            };
            createController = function() {
                $injector.get('$controller')("RolePermisoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:rolePermisoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
