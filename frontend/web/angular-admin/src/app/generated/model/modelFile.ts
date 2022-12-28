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

export interface ModelFile { 
    absolute?: boolean;
    absoluteFile?: Blob;
    absolutePath?: string;
    canonicalFile?: Blob;
    canonicalPath?: string;
    directory?: boolean;
    file?: boolean;
    freeSpace?: number;
    hidden?: boolean;
    name?: string;
    parent?: string;
    parentFile?: Blob;
    path?: string;
    totalSpace?: number;
    usableSpace?: number;
}