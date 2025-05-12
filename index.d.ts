declare module 'react-native-contact-picker' {
  export type Contact = {
    mobile: string;
    name: string;
  };

  function pickContact(): Promise<Contact>;

  export { pickContact };
}
