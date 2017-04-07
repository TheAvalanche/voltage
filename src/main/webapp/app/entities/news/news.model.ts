
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class News {
    constructor(
        public id?: number,
        public created?: any,
        public updated?: any,
        public version?: number,
        public title?: string,
        public body?: any,
        public image?: any,
        public language?: Language,
    ) {
    }
}
