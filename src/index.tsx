import { NativeModules, Platform } from 'react-native';
import type { Contact } from 'react-native-choose-contact';

const LINKING_ERROR =
  `The package 'react-native-choose-contact' doesn't seem to be linked. Make sure: \n\n` +
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
