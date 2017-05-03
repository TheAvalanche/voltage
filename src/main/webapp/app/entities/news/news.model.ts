
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class News {
    constructor(
        public id?: number,
        public created?: any,
        public title?: string,
        public body?: any,
        public imageUrl?: string,
        public language?: Language,
    ) {
    }
}
