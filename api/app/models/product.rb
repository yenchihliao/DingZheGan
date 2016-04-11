class Product < ActiveRecord::Base
  belongs_to :company

  validates_numericality_of :number
  validates_presence_of :name
  validates_numericality_of :price
end
