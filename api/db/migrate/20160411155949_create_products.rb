class CreateProducts < ActiveRecord::Migration
  def change
    create_table :products, id: false do |t|
      t.primary_key :ProductSN
      t.integer :ProductVendor
      t.string :ProductTitle
      t.string :ProductNo
      t.integer :SellPrice
      t.float :SellPriceCNY
      t.integer :ProductQuantity
      t.string :ProductIntroduction
      t.string :StyleTitleA
      t.string :StyleTitleB
      t.string :LargeIcon
      t.string :SmallIcon
      t.string :ProductPhoto1
      t.string :ProductPhoto2
      t.string :ProductPhoto3
      t.string :ProductPhoto4
      t.string :ProductPhoto5
      t.string :ProductPhoto6
      t.string :ProductPhoto7
      t.string :ProductPhoto8

      t.timestamps null: false
    end

    add_index :products, :ProductSN, unique:true
  end
end
