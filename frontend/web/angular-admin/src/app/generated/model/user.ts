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
import { UserRole } from './userRole';

export interface User { 
    birthDate?: Date;
    clearPassword?: string;
    comment?: string;
    createdOn?: Date;
    email?: string;
    firstName?: string;
    id?: number;
    lastName?: string;
    lastUpdateOn?: Date;
    password?: string;
    permissionList?: Array<string>;
    phone?: string;
    roleList?: Array<string>;
    specialist?: Specialist;
    status?: number;
    userImagePath?: string;
    username?: string;
    usersRolesList?: Array<UserRole>;
}