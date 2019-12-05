/**
 * Created by xiaodou_chenxiaoxiang on 16/8/2.
 */
/**
 * Created by xiaodou_chenxiaoxiang on 16/7/28.
 */
var uuid = require('node-uuid');

module.exports = {
  connection: 'apiDatabase',
  tableName: 'xd_autotest_collection',
  attributes: {

    //主键, _postman_id
    id: {
      type: 'string',
      required: true,
      primaryKey: true,
      defaultsTo: function () {
        return uuid.v4();
      }
    },
    schema:{
      type:'string',
      required:true
    },

    //collection name
    name: {
      type: 'string',
      required: true,
      unique:true,
      minLength: 1,
      maxLength: 60
    },

    //可用状态
    disabled:{
      type:'boolean',
      required: false
    },

    //描述
    description: {
      type: 'string',
      required: false,
      minLength: 1,
      maxLength: 300
    },

    //变量数组
    variables:{
      type:'array',
      required:false
    },

    item:{
      type:'array',
      required:false
    }
  }
};

