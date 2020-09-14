function Item(data) {
    /*"item_id,type,name,grade,xx,owner,runable"*/
    this.item_id = data.item_id;
    this.type = data.type;
    this.name = data.name;
    this.grade = data.grade;
    this.xx = data.xx;
    this.owner = data.owner;
    this.runable = data.runable;
}

function Knapsack() {

}

Knapsack.prototype.setItemList = function (para) {
    this.item_list = para;
};

Item.prototype.setItem_id = function (para) {
    this.item_id = para;
};
Item.prototype.getItem_id = function () {
    return this.item_id;
};
Item.prototype.setType = function (para) {
    this.type = para;
};
Item.prototype.getType = function () {
    return this.type;
};
Item.prototype.setName = function (para) {
    this.name = para;
};
Item.prototype.getName = function () {
    return this.name;
};
Item.prototype.setGrade = function (para) {
    this.grade = para;
};
Item.prototype.getGrade = function () {
    return this.grade;
};
Item.prototype.setXx = function (para) {
    this.xx = para;
};
Item.prototype.getXx = function () {
    return this.xx;
};
Item.prototype.setOwner = function (para) {
    this.owner = para;
};
Item.prototype.getOwner = function () {
    return this.owner;
};
Item.prototype.setRunable = function (para) {
    this.runable = para;
};
Item.prototype.getRunable = function () {
    return this.runable;
};

