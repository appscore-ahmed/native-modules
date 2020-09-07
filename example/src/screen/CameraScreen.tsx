import React from 'react';
import { Text, View, StyleSheet, Image, Button, Alert } from 'react-native';
import NativeModule from '../CustomModules';

const CameraScreen = () => {
  const [imageSource, setImageSource] = React.useState<string>(
    'file://storage/emulated/0/Pictures/Title.jpg'
  );
  const [text, setText] = React.useState<string | undefined>();

  return (
    <View style={styles.container}>
      <Text>{text}</Text>
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
};

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

export default CameraScreen;
