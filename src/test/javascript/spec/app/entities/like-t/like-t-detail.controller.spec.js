'use strict';

describe('Controller Tests', function() {

    describe('LikeT Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLikeT, MockUsuario, MockPublicacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLikeT = jasmine.createSpy('MockLikeT');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockPublicacion = jasmine.createSpy('MockPublicacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LikeT': MockLikeT,
                'Usuario': MockUsuario,
                'Publicacion': MockPublicacion
            };
            createController = function() {
                $injector.get('$controller')("LikeTDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'digitalBlogApp:likeTUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
