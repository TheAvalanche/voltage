
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class Blog {
    constructor(
        public id?: number,
        public created?: any,
        public title?: string,
        public body?: any,
        public language?: Language,
        public blogCategoryId?: number,
    ) {
    }
}
