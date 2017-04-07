
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class BlogCategory {
    constructor(
        public id?: number,
        public created?: any,
        public updated?: any,
        public version?: number,
        public title?: string,
        public language?: Language,
    ) {
    }
}
