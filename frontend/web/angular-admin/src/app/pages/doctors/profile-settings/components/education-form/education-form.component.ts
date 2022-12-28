import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-education-form',
  templateUrl: './education-form.component.html',
  styleUrls: ['./education-form.component.scss']
})
export class EducationFormComponent implements OnInit {
  @Input() items: { degree: string, college: string, yearOfCompletion: string }[] = [
    { degree: '', college: '', yearOfCompletion: ''}
  ];
  @Output() onChange: EventEmitter<{ degree: string, college: string, yearOfCompletion: string }[]> = new EventEmitter<{ degree: string, college: string, yearOfCompletion: string }[]>();

  constructor() { }

  public ngOnInit(): void {
  }

  public addSlot(): void {
    if (!this.verify()) {
      alert('The slot you add already exist');
      return;
    }
    const item: { degree: string, college: string, yearOfCompletion: string } = {
      degree: '',
      college: '',
      yearOfCompletion: ''
    };
    this.onChange.emit(this.items);
    this.items.push(item);
  }

  public verify(): boolean {
    if (this.items && this.items.length > 0) {
      const lastSlot = this.items[this.items.length - 1];
      if (lastSlot.degree === '' || lastSlot.college === '' || lastSlot.yearOfCompletion === '') {
        return false;
      }
      for (let i = 0; i < this.items.length - 1; i++) {
        const item = this.items[i];
        if (item.degree === lastSlot.degree && item.college === lastSlot.college && item.yearOfCompletion === lastSlot.yearOfCompletion) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public remove(item: { degree: string, college: string, yearOfCompletion: string }): void {
    const index: number = this.items.findIndex(s => (s === item));
    if (index !== -1) {
      this.items.splice(index, 1);
      this.saveChanges();
      return;
    }
    return;
  }

  public saveChanges(): void {
    this.items.forEach((slot, i) => {
      if (slot.degree === '' || slot.college === '' || slot.yearOfCompletion === '') {
        this.items.splice(i, 1);
      }
    });
    let items: { degree: string, college: string, yearOfCompletion: string }[] = [];
    items.push(...this.items);
    items = items.filter((value, index, self) =>
      index === self.findIndex((t) => (
        t.degree === value.degree && t.college === value.college && t.yearOfCompletion === value.yearOfCompletion
      ))
    );
    this.items = items;
    this.onChange.emit(this.items);
  }
}
