import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'sliceParagraph'})
export class SliceParagraphPipe implements PipeTransform {

    transform(htmlText: string): string {
        console.log(htmlText.indexOf('<p>' + 3));
        console.log(htmlText.indexOf('</p>'));
        return htmlText.slice(htmlText.indexOf('<p>') + 3, htmlText.indexOf('</p>')).concat('..');
    }
}
