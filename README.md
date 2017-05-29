# PopularMovies
A Functioning Android App which I built for Udacity Associate Android Developer FastTrack program sponsored by Andela and supported by Google. 

## Feature
* This app implements the MVP pattern, RxJava, Dependency Injection(Custom).
* This app showcases responsive UI both for phone and tablet devices.
* Discover the most popular, the most rated or the highest rated movies
* Save favorite movies locally to view them even when offline
* Watch Movie trailers and Read reviews


## Getting Started

You can clone this repo by running this command ``` git clone ``` on git bash or linux terminal.

### Prerequisites

To run the App on your device, you need to get api key from api.themoviedb.org

Check out the App level build.gradle file, you will see where to paste your api key there

```  it.buildConfigField 'String', 'MOVIE_API_KEY', "\"PUT YOUR API KEY HERE\"" ```

with your api 

### Libraries
* RxJava
* RxAndroid
* Retrofit
* ButterKnife
* Gson
* Okhttp3
* Glide
* Guava
* Mockito

![Video](https://cloud.githubusercontent.com/assets/18694890/26163185/cceec030-3b20-11e7-9565-89e326a67434.gif)
![Video](https://cloud.githubusercontent.com/assets/18694890/26163334/41326f78-3b21-11e7-8655-2f9d99d5fcf8.gif)

![Phone](https://cloud.githubusercontent.com/assets/18694890/26162310/48e480a6-3b1e-11e7-8898-f205141586c9.png)

![Phone](https://cloud.githubusercontent.com/assets/18694890/26162308/48e21b18-3b1e-11e7-97e7-a36a284581d2.png)

![Tablet](https://cloud.githubusercontent.com/assets/18694890/26162336/63c85a3c-3b1e-11e7-99b4-6a3cc78a8bbb.png)

### Contributors on GitHub
Please feel free to make a pull request. There is so much to improve on.

Working on refractoring the app to use Dagger 2 for dependency Injection in place of the Custom Injection implemented

Also few tests have been written, and hopefully we can have more from your contribution so to fix those hidden bugs and ensure the project is safe to use for learning

#### Developer
* e-mail : kanyinsolafapohunda@gmail.com
* Twitter: [@codedentwickler](https://twitter.com/codedentwickler "codedentwickler on twitter")


##### LICENSE

```

MIT License

Copyright (c) [2017] [Kanyinsola Fapohunda Oyindamola]

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
SOFTWARE
```
