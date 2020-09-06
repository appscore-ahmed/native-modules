import * as React from 'react';
import { StyleSheet, View, Text, Button, Alert, Image } from 'react-native';
// import ToastExample from './ToastExample';
import NativeModule from './CustomModules';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [resultAdd, setResultAdd] = React.useState<number | undefined>();
  const [text, setText] = React.useState<string | undefined>();
  const [imageSource, setImageSource] = React.useState<string>(
    'file://storage/emulated/0/Pictures/Title.jpg'
  );

  // const promiseTest = async () => {
  //   try {
  //     var a = ToastExample.callbackMethodWithPromise(123);
  //     setResult(a);
  //   } catch (e) {
  //     setText(e);
  //   }
  // };

  React.useEffect(() => {
    // AwesomeModule.multiply(19, 7).then(setResult);
    // AwesomeModule.add(2, 3).then(setResultAdd);

    NativeModule.ToastExample.show('test', NativeModule.ToastExample.SHORT);
    NativeModule.ToastExample.showLonger('message');

    NativeModule.ToastExample.returnSomeString().then(setText);
    NativeModule.ToastExample.callbackMethod(
      23,
      (msg: any) => {
        setResultAdd(msg);
        NativeModule.ToastExample.showLonger(`${msg}`);
      },
      (msg: any) => {
        setResult(msg);
      }
    );
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result multiply: {result}</Text>
      <Text>Result addition: {resultAdd}</Text>
      <Text>demo text: {text}</Text>
      <Button
        title="Click Meee"
        onPress={() => {
          NativeModule.CameraModule.callCamera()
            .then((uri: string) => {
              console.log(`uri : ${uri}`);
              setImageSource(uri);
              setText(uri);
            })
            .catch((e: string) => Alert.alert(e));
        }}
      />
      <Image
        // style={styles.stretch}
        source={{ uri: imageSource, width: 200, height: 200 }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  stretch: {
    width: 200,
    height: 200,
    resizeMode: 'stretch',
  },
});
