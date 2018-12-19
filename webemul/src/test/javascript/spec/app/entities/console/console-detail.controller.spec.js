'use strict';

describe('Controller Tests', function() {

    describe('Console Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConsole, MockEmulator, MockRom;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConsole = jasmine.createSpy('MockConsole');
            MockEmulator = jasmine.createSpy('MockEmulator');
            MockRom = jasmine.createSpy('MockRom');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Console': MockConsole,
                'Emulator': MockEmulator,
                'Rom': MockRom
            };
            createController = function() {
                $injector.get('$controller')("ConsoleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'webApp:consoleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
