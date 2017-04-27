
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class BlogCategory {
    constructor(
        public id?: number,
        public title?: string,
        public language?: Language,
    ) {
    }
}
