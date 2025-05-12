import { NativeModules, Platform } from 'react-native';
import type { Contact } from 'react-native-contact-picker';

const LINKING_ERROR =
  `The package 'react-native-contact-picker' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ContactPickerModule = NativeModules.ContactPickerModule
  ? NativeModules.ContactPickerModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function pickContact(): Promise<Contact> {
  return ContactPickerModule.pickContact();
}
