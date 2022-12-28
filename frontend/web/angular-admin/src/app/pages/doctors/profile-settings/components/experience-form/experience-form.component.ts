import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-experience-form',
  templateUrl: './experience-form.component.html',
  styleUrls: ['./experience-form.component.scss']
})
export class ExperienceFormComponent implements OnInit {
  @Input() items: { hospitalName: string, from: string, to: string, designation: string }[] = [
    { hospitalName: '', from: '', to: '', designation: '' }
  ];
  @Output() onChange: EventEmitter<{ hospitalName: string, from: string, to: string, designation: string }[]> = new EventEmitter<{ hospitalName: string, from: string, to: string, designation: string }[]>();

  constructor() { }

  public ngOnInit(): void {
  }

  public addSlot(): void {
    if (!this.verify()) {
      alert('The slot you add already exist');
      return;
    }
    const item: { hospitalName: string, from: string, to: string, designation: string } = { 
      hospitalName: '', from: '', to: '', designation: '' 
    };
    this.onChange.emit(this.items);
    this.items.push(item);
  }

  public verify(): boolean {
    if (this.items && this.items.length > 0) {
      const lastSlot = this.items[this.items.length - 1];
      if (lastSlot.hospitalName === '' || lastSlot.from === '' || lastSlot.to === '') {
        return false;
      }
      for (let i = 0; i < this.items.length - 1; i++) {
        const item = this.items[i];
        if (item.hospitalName === lastSlot.hospitalName && item.from === lastSlot.from && item.to === lastSlot.to && item.designation === lastSlot.designation) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public remove(item: { hospitalName: string, from: string, to: string, designation: string }): void {
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
      if (slot.hospitalName === '' || slot.from === '' || slot.to === '') {
        this.items.splice(i, 1);
      }
    });
    let items: { hospitalName: string, from: string, to: string, designation: string }[] = [];
    items.push(...this.items);
    items = items.filter((value, index, self) =>
      index === self.findIndex((t) => (
        t.hospitalName === value.hospitalName && t.from === value.from && t.to === value.to && t.designation === value.designation
      ))
    );
    this.items = items;
    this.onChange.emit(this.items);
  }
}
