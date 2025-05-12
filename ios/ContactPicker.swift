import Foundation
import ContactsUI
import React

@objc(ContactPickerModule)
class ContactPickerModule: NSObject, CNContactPickerDelegate {

  var resolver: RCTPromiseResolveBlock?
  var rejecter: RCTPromiseRejectBlock?

  @objc(pickContact:rejecter:)
  func pickContact(_ resolve: @escaping RCTPromiseResolveBlock,
                   rejecter reject: @escaping RCTPromiseRejectBlock) {
    DispatchQueue.main.async {
      guard let rootVC = UIApplication.shared.delegate?.window??.rootViewController else {
        reject("no_root", "Root view controller not found", nil)
        return
      }

      self.resolver = resolve
      self.rejecter = reject

      let picker = CNContactPickerViewController()
      picker.delegate = self
      rootVC.present(picker, animated: true, completion: nil)
    }
  }

  func contactPicker(_ picker: CNContactPickerViewController, didSelect contact: CNContact) {
    let phone = contact.phoneNumbers.first?.value.stringValue ?? ""
    let name = CNContactFormatter.string(from: contact, style: .fullName) ?? "بدون نام"
    let result: [String: String] = ["name": name, "phone": phone]
    resolver?(result)
  }

  func contactPickerDidCancel(_ picker: CNContactPickerViewController) {
    rejecter?("cancelled", "User cancelled contact picker", nil)
  }
}
