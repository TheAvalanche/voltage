
const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class Slide {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public imageUrl?: string,
        public language?: Language,
    ) {
    }
}
