# react-native-contact-picker

Pick contact

## Installation

```sh
npm install react-native-contact-picker
```

## Usage

```js
import { pickContact } from 'react-native-contact-picker';

// ...

const contact = await pickContact();
```

### TypeScript Support

This package includes TypeScript type definitions:

```typescript
type Contact = {
  mobile: string;
  name: string;
};
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---
