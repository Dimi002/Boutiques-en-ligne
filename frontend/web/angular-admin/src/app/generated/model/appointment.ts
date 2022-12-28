/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { Specialist } from './specialist';

export interface Appointment { 
    appointmentDate?: Date;
    appointmentHour?: Date;
    appointmentId?: number;
    createdOn?: Date;
    lastUpdateOn?: Date;
    patientEmail?: string;
    patientMessage?: string;
    patientName?: string;
    patientPhone?: string;
    specialistId?: Specialist;
    state?: string;
    status?: number;
}