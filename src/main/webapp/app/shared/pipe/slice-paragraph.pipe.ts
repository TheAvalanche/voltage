import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'sliceParagraph'})
export class SliceParagraphPipe implements PipeTransform {

    transform(htmlText: string): string {
        return htmlText.slice(htmlText.indexOf('<p>') + 3, htmlText.indexOf('</p>')).concat('..');
    }
}
