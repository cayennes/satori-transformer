# satori-transformer

Command line clojure app to transform the csv that satori reader will export into a slightly different csv that matches what I want to import into anki

## Why

[Satori Reader](https://satorireader.com) is great. And one of my favorite features is that I can export items to [anki](https://ankisrs.net). But it takes some small changes to be exactly what I want:
* Add spaces before the kanji so the furigana appears over the correct word.
* sentence oriented: one context sentence with multiple words instead of the other way around.
* output just the fields I want, in order, so that I don't need to fiddle with a bunch of giant dropdowns every time I import.

Yes this would be better as an anki addon. Using clojure was the fastest way to solve my problem so that's what I did (more time to do other things).

I have no idea if this will be useful to anyone else, and I am not likely to give it any significant improvements, but here it is just in case.

## Usage

Requires [leiningen](https://leiningen.org).

1. Edit `src/satori_transformer/core.clj` to configure what fields you want.
2. Download and unzip satori reader export file and put the `export.csv` in this directory
3. Create `exported-for-anki.csv` by running `lein run`
4. In anki, import that

## License

MIT License

Copyright (c) 2019 Cayenne Geis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
