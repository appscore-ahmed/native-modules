import * as React from 'react';
import { StyleSheet, View, Text } from 'react-native';
// import AwesomeModule from 'react-native-awesome-module';
import ToastExample from './ToastExample';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [resultAdd, setResultAdd] = React.useState<number | undefined>();
  const [text, setText] = React.useState<string | undefined>();

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

    ToastExample.show('test', ToastExample.SHORT);
    ToastExample.showLonger('message');

    ToastExample.returnSomeString().then(setText);
    ToastExample.callbackMethod(
      23,
      (msg: any) => {
        setResultAdd(msg);
        ToastExample.showLonger(`${msg}`);
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
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
