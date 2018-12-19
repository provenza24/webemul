'use strict';

describe('Controller Tests', function() {

    describe('Console Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConsole, MockRom, MockEmulator;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConsole = jasmine.createSpy('MockConsole');
            MockRom = jasmine.createSpy('MockRom');
            MockEmulator = jasmine.createSpy('MockEmulator');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Console': MockConsole,
                'Rom': MockRom,
                'Emulator': MockEmulator
            };
            createController = function() {
                $injector.get('$controller')("ConsoleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'webEmulApp:consoleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
