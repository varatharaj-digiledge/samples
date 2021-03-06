/**
 * 
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

import ApiClient from '../ApiClient';

/**
 * The NetCordaCoreNodeServicesVaultColumnObjectObject model module.
 * @module io.generated.model/NetCordaCoreNodeServicesVaultColumnObjectObject
 * @version 1.0.0
 */
class NetCordaCoreNodeServicesVaultColumnObjectObject {
    /**
     * Constructs a new <code>NetCordaCoreNodeServicesVaultColumnObjectObject</code>.
     * @alias module:io.generated.model/NetCordaCoreNodeServicesVaultColumnObjectObject
     * @param name {String} 
     * @param declaringClass {String} Java class name
     */
    constructor(name, declaringClass) { 
        
        NetCordaCoreNodeServicesVaultColumnObjectObject.initialize(this, name, declaringClass);
    }

    /**
     * Initializes the fields of this object.
     * This method is used by the constructors of any subclasses, in order to implement multiple inheritance (mix-ins).
     * Only for internal use.
     */
    static initialize(obj, name, declaringClass) { 
        obj['name'] = name;
        obj['declaringClass'] = declaringClass;
    }

    /**
     * Constructs a <code>NetCordaCoreNodeServicesVaultColumnObjectObject</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:io.generated.model/NetCordaCoreNodeServicesVaultColumnObjectObject} obj Optional instance to populate.
     * @return {module:io.generated.model/NetCordaCoreNodeServicesVaultColumnObjectObject} The populated <code>NetCordaCoreNodeServicesVaultColumnObjectObject</code> instance.
     */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new NetCordaCoreNodeServicesVaultColumnObjectObject();

            if (data.hasOwnProperty('name')) {
                obj['name'] = ApiClient.convertToType(data['name'], 'String');
            }
            if (data.hasOwnProperty('declaringClass')) {
                obj['declaringClass'] = ApiClient.convertToType(data['declaringClass'], 'String');
            }
        }
        return obj;
    }


}

/**
 * @member {String} name
 */
NetCordaCoreNodeServicesVaultColumnObjectObject.prototype['name'] = undefined;

/**
 * Java class name
 * @member {String} declaringClass
 */
NetCordaCoreNodeServicesVaultColumnObjectObject.prototype['declaringClass'] = undefined;






export default NetCordaCoreNodeServicesVaultColumnObjectObject;

