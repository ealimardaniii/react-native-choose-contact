declare module 'react-native-choose-contact' {
  export type Contact = {
    mobile: string;
    name: string;
  };

  function pickContact(): Promise<Contact>;

  export { pickContact };
}
