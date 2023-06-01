// Tommy Ekberg toek3476
// Assignment 1, Prototype-Based Multiple Inheritance

let myObject = {
    arr:[],
    create : function(){

        // If no arguments --> Create an object
        if(arguments[0] === null){
            let newObject = Object.create(this);
            newObject.arr = [];
            return newObject;
        }else{
            let newObject = Object.create(this);
            for(let i = 0; i <= arguments[0].length - 1; i++){

                //Circular prevention
                this.addPrototype(arguments[0][i]);

                //Pushes the object to inherit from into an array
                newObject.arr.push(arguments[0][i]);
            }
            return newObject;
        }
    },
    call : function(funcName, parameters){
        // Recursion methods
        let recur;

        // When funcName is found, return it
        if(this.hasOwnProperty(funcName)){
            return this[funcName](parameters);

        // Recursion part, goes through all objects inside array in a Depth-First-Search.
        }else if(this.arr.length !== 0){
            for(let i = 0; i < this.arr.length; i++){
                if(recur === undefined){
                    recur = this.__proto__.arr[i].call(funcName, parameters);
                }else{
                    return recur
                }
            }
        }
    },
    // Circular prevention method
    addPrototype : function(obj){
        if(obj.__proto__.arr !== null || obj.__proto__.arr !== 0){
            for(let i = 0; i < obj.__proto__.arr.length; i++) {
                if(obj.__proto__.arr[i] === this){
                    throw Error('circular inheritance.');
                }
            }
        }
    }

}
var obj0 = myObject.create(null);
obj0.func = function(arg) { return "func0: " + arg; };
var obj1 = myObject.create([obj0]);
var obj2 = myObject.create([]);

obj2.func = function(arg) { return "func2: " + arg; };
var obj3 = myObject.create([obj1, obj2]);
var result = obj3.call("func", ["hello"]) ;
console.log("should print ’func0: hello’ ->", result);




