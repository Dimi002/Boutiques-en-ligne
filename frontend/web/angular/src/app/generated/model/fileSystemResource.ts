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
import { InputStream } from './inputStream';
import { OutputStream } from './outputStream';
import { URI } from './uRI';
import { URL } from './uRL';

export interface FileSystemResource { 
    description?: string;
    file?: Blob;
    filename?: string;
    inputStream?: InputStream;
    open?: boolean;
    outputStream?: OutputStream;
    path?: string;
    readable?: boolean;
    uri?: URI;
    url?: URL;
    writable?: boolean;
}