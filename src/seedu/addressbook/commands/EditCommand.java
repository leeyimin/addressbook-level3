package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edit a person entry identified by the index number used in the last person listing to the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: INDEX [NAME] [[p]p/PHONE] [[p]e/EMAIL] [[p]a/ADDRESS]  \n\t"
            + "Example: " + COMMAND_WORD
            + " 1 John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Person edited: %1$s";

    private Phone phone;
    private Email email;
    private Address address;
    private Name name;
    
    public EditCommand(int targetVisibleIndex, String name, 
            String phone, boolean isPhonePrivate,
            String email, boolean isEmailPrivate,
            String address, boolean isAddressPrivate) throws IllegalValueException {
        
        super(targetVisibleIndex);
        if(name != null){
            this.name = new Name(name);
        }
        if(phone != null){
            this.phone = new Phone(phone, isPhonePrivate);
        }
        if(email != null){
            this.email = new Email(email, isEmailPrivate);
        }
        if(address != null){
            this.address = new Address(address, isAddressPrivate);
        }
        
    }

    @Override
    public CommandResult execute() {
        try {
            final Person target = getWriteablePerson();
            addressBook.removePerson(target);
            return new CommandResult(String.format(MESSAGE_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

}
