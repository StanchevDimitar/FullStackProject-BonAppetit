export class Recipe {
    id!: number;
    name!: string;
    ingredients!: string;
    category!: string;
    addedBy: string;

    constructor(){
        this.addedBy = "Ivaan"
    }

    // constructor(){
    //     this.name = "";
    //     this.ingredients = "";
    //     this.category = "";
    //     this.creatorName = "";
    // }
}
