"use strict"

function create(row){
    function image(){
        this.id = null;
        this.name = null;
        this.src = null;
        this.desc = null;
        this.status = null;
    }
    if(row) {
        var model = new image();
        model.id = row['id'];
        model.name = row['name'];
        model.src = row['src'];
        model.desc = row['desc'];
        model.status = row['status'];
        return model;
    }
    throw new Error("数据错误！");
}

exports.createOne = create;
exports.createList = function(results){
    if(results) {
        var list = [];
        for ( var row in results){
            list.push(create(results[row]));
        }
        return list;
    }
    throw new Error("数据错误！");
}