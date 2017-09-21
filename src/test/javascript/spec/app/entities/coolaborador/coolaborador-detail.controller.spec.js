'use strict';

describe('Controller Tests', function() {

    describe('Coolaborador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCoolaborador, MockCapitulo, MockPublicacion, MockUsuario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCoolaborador = jasmine.createSpy('MockCoolaborador');
            MockCapitulo = jasmine.createSpy('MockCapitulo');
            MockPublicacion = jasmine.createSpy('MockPublicacion');
            MockUsuario = jasmine.createSpy('MockUsuario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Coolaborador': MockCoolaborador,
                'Capitulo': MockCapitulo,
                'Publicacion': MockPublicacion,
                'Usuario': MockUsuario
            };
            createController = function() {
                $injector.get('$controller')("CoolaboradorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:coolaboradorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
