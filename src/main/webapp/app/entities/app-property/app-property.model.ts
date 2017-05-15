
const enum AppPropertyType {
    'CONTACT',
    'SOCIAL',
    'OTHER'
};

const enum Language {
    'RUSSIAN',
    'ENGLISH',
    'LATVIAN'

};
export class AppProperty {
    constructor(
        public id?: number,
        public propertyType?: AppPropertyType,
        public name?: string,
        public value?: string,
        public language?: Language,
    ) {
    }
}
