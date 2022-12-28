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
import { SpecialistSpecialityId } from './specialistSpecialityId';
import { Speciality } from './speciality';

export interface SpecialistSpeciality { 
    createdOn?: Date;
    lastUpdateOn?: Date;
    specialist?: Specialist;
    specialistSpecialityId?: SpecialistSpecialityId;
    speciality?: Speciality;
    status?: number;
}