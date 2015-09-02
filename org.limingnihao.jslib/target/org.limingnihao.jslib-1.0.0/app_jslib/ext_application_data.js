
/**---------------------------------------公共的------------------------------------*/

Ext.define("ValueNameBean",{
	extend: "Ext.data.Model",
	fields: [
	    { name: "value", type: "int" },
	    { name: "name",  type: "string" }
	]
});

Ext.define("ApplicationBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "uid", 			 type: "int" },
		{ name: "propertyName",  type: "string" },
		{ name: "propertyFlag",  type: "string" },
		{ name: "propertyValue", type: "string" },
		{ name: "description", 	 type: "string" }
    ]
});

Ext.define("AttributeBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "attributeValue", type: "int" },
		{ name: "attributeName",  type: "string" }
    ]
});

Ext.define("VersionBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "versionId", 		 type: "int" },
        { name: "versionType",		 type: "int" },
        { name: "versionTypeName",   type: "string" },
        { name: "versionName",		 type: "string" },
        { name: "versionCode",		 type: "string" },
		{ name: "deviceType", 		 type: "int" },
		{ name: "deviceTypeName",	 type: "string" },
		{ name: "fileName",     	 type: "string" },
		{ name: "downUrl", 			 type: "string" },
		{ name: "fileMd5", 			 type: "string"},
		{ name: "description", 	     type: "string" },
		{ name: "uploadTime",        type: "string" }
    ]
});

Ext.define("UserBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "userId", 	    type: "int" },
		{ name: "username",     type: "string" },
		{ name: "password",     type: "string" },
		{ name: "nickname",     type: "string" },
		{ name: "createTime",   type: "string" },
		{ name: "lastTime",     type: "string" },
		{ name: "groupIds", 	type: "array" },
		{ name: "groupNames", 	type: "string" },
		{ name: "roleIds", 		type: "array" },
		{ name: "roleNames", 	type: "string" },
		{ name: "useFlag", 		type: "int" },
		{ name: "useFlagName", 	type: "string" },
		{ name: "userType",     type: "int" },
		{ name: "userTypeName", type: "string" },
		{ name: "attendType",   type: "int" },
		{ name: "isOnline",     type: "int" }
    ]
});

Ext.define("GroupBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "id", 		   type: "string" },
        { name: "text", 	   type: "string" },
        { name: "groupId", 	   type: "int" },
		{ name: "groupName",   type: "string" },
		{ name: "parentId",    type: "int" },
		{ name: "parentName",  type: "string" },
		{ name: "description", type: "string" },
		{ name: "sequence",    type: "int" },
		{ name: "regionId",    type: "int" },
		{ name: "regionName",  type: "string" },
		{ name: "useFlag", 	   type: "int" },
		{ name: "useFlagName", type: "string" }
    ]
});

Ext.define("GroupTreeBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "id", 		   type: "string" },
        { name: "text", 	   type: "string" },
        { name: "leaf", 	   type: "boolean" },
        { name: "groupId", 	   type: "int" },
		{ name: "groupName",   type: "string" },
		{ name: "parentId",    type: "int" },
		{ name: "parentName",  type: "string" },
		{ name: "description", type: "string" },
		{ name: "sequence",    type: "int" },
		{ name: "regionId",    type: "int" },
		{ name: "regionName",  type: "string" },
		{ name: "useFlag", 	   type: "int" },
		{ name: "useFlagName", type: "string" }
    ]
});

Ext.define("RegionBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "regionId",   type: "int" },
		{ name: "regionName", type: "string" },
		{ name: "regionCode", type: "int" },
		{ name: "mapName", type: "string" }
    ]
});

Ext.define("RegionTreeBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "id", 		  type: "string" },
        { name: "text", 	  type: "string" },
        { name: "leaf", 	  type: "boolean" },
        { name: "regionId",   type: "int" },
		{ name: "regionName", type: "string" },
		{ name: "parentId",   type: "int" },
		{ name: "parentName", type: "string" },
		{ name: "sequence",   type: "int" },
		{ name: "useFlag", 	  type: "int" },
		{ name: "useFlagName",type: "string" }
    ]
});

Ext.define("ResourceBean", {
	extend: "Ext.data.Model",
	fields: [
        { name: "resourceId", 	type: "int" },
        { name: "resourceName", type: "string" },
        { name: "childrenList", type: "object" }
	]
});

Ext.define("RoleBean", {
    extend: "Ext.data.Model",
    fields: [
        { name: "roleId", 		  type: "int" },
        { name: "roleName", 	  type: "string" },
        { name: "authorityIds",   type: "array" },
		{ name: "authorityNames", type: "array" },
		{ name: "authoritys", 	  type: "array" },
        { name: "useFlag", 		  type: "int" },
        { name: "useFlagName",    type: "string" }
    ]
});

