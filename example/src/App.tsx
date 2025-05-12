import { useState } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import { pickContact } from 'react-native-contact-picker';

export default function App() {
  const [contact, setContact] = useState('');

  const handlePress = async () => {
    try {
      const contact = await pickContact();
      setContact(JSON.stringify(contact));
    } catch {
      setContact('Error occured');
    }
  };

  return (
    <View style={styles.container}>
      <Button title="Pick contact" onPress={handlePress} />
      <Text>{contact}</Text>
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
