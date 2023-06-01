// Tommy Ekberg toek3476
// Assignment 1, Class-Based Multiple Inheritance

function createClass(className, superClassList){

    // Create a class
    let newObject = Object.create(this);

    this.className = className;
    newObject.arr = [];
    newObject.className = className;

    // New method
    newObject.new = function(){
        // Call method inside New method.
        newObject.call = function(funcName, parameters){
            let recur;
            if(this.hasOwnProperty(funcName)){
                return this[funcName](parameters);
            }else if(this.arr.length !== 0){
                for(let i = 0; i < this.arr.length; i++) {
                    if(recur === undefined){
                        return this.arr[i].new().call(funcName, parameters);
                    }else{
                        return recur;
                    }
                }
            }
        }
        return this;
    }

    // Circular inheritance prevention
    newObject.addSuperClass = function(obj){
        if(obj.arr !== null || obj.arr !== 0){
            for(let i = 0; i < obj.arr.length; i++) {
                if(obj.arr[i] === this){
                    throw Error('circular inheritance.');
                }
            }
        }
    }
    // Adds inheritance inside an Array
    if(superClassList !== null){
        for(let i = 0; i <= superClassList.length - 1; i++){
            // Circular prevention
            newObject.addSuperClass(superClassList[i]);

            newObject.arr.push(superClassList[i]);
        }
    }
    return newObject;
}
