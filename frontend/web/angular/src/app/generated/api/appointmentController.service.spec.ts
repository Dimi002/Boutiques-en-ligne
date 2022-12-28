import { HttpClient } from "@angular/common/http";
import { TestBed } from "@angular/core/testing";
import { Configuration } from "../configuration";
import { Appointment } from "../model/appointment";
import { Specialist } from "../model/specialist";
import { AppointmentControllerService } from "./appointmentController.service";

describe('Appointment Service', () => {

    let httpClientSpy: jasmine.SpyObj<HttpClient>;
    let appointmentService: AppointmentControllerService;
    let basePath = '//localhost:8077/';
    let configuration = new Configuration();
    beforeEach(async () => {
        httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
        appointmentService = new AppointmentControllerService(httpClientSpy, basePath, configuration);
    });


    it('should create Appointment Using POST', () => {
        // const specialist: Specialist = {
        //     specialistId: 1
        // }
        // const appointment: Appointment = {
        //     appointmentDate: new Date(),
        //     appointmentHour: new Date(),
        //     patientEmail: 'tchamouramses@gmail.com',
        //     patientMessage: 'merci pour ma prise en comte',
        //     patientName: 'TEST JASMINE',
        //     patientPhone: '651771191',
        //     specialistId: specialist,
        //     state: 'PENDING',
        //     status: 1
        // }

        // appointmentService.createAppointmentUsingPOST(appointment).subscribe((res) => {
        //     console.log(res);

        // });
    });
});
