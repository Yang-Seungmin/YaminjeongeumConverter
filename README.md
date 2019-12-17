# Yaminjeongeum Converter
###### Copyright (C) 2019 by Seungmin-Yang | <tmdals099@gmail.com>

한글 단어 및 문자 변환과 관련된 밈인 '야민정음'으로의 변환 및 역변환을 제공하는 툴입니다.

## Download JAR file
[Click Here](https://github.com/Yang-Seungmin/YaminjeongeumConverter/raw/master/out/artifacts/YaminjeongeumConverter_jar/YaminjeongeumConverter.jar)

## Usage
### Create new Instance
YaminjeongeumConverter.Builder()를 사용해 YaminjeongeumConverter 인스턴스를 생성합니다.
```
YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
                .build();
```
### Convert
변환하고 싶은 문자열을 convert 메소드와 함께 추가하면 변환된 문자열이 반환됩니다.
```
yaminjeongeumConverter.convert(str);
```

### Additional Features
```
YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
                .setConvertHanja(false)
                .setSelectionStrength(SelectionStrength.EXTREME)
                .setDebugMode(false)
                .setFilePath("custom_file_path.json")
                .build();
```
**setConvertHanja(boolean convertHanja)** : 한자 문자열 간의 변환을 허용할 지 결정합니다. 한자 폰트가 없을 경우 유용합니다.  
**setFilePath(String filePath)** : Custom json file을 사용할 경우 사용하며 json file의 규칙이 동일해야 합니다.  
**setSelectionStrength(SelectionStrength selectionStrength)** : 변환 강도를 지정합니다.
> SelectionStrength.ORIGINAL : 야민정음으로 된 문자열을 역변환할 때 사용합니다.  
> SelectionStrength.SLIGHT : 약한 강도의 변환을 수행합니다.  
> SelectionStrength.MODERATE : 중간 강도의 변환을 수행합니다.  
> SelectionStrength.EXTREME : 강력한 변환을 수행합니다.  

**setDebugMode(boolean debugMode)** : 이 옵션이 true일 경우 중간 과정을 콘솔(System.out.println)로 출력합니다.  

## Library in use
Gson - A Java serialization/deserialization library to convert Java Objects into JSON and back
<https://github.com/google/gson>
```
Copyright 2008 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
